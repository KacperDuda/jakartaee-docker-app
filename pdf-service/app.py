from flask import Flask, request, send_file, abort
from jinja2 import Environment, FileSystemLoader
import subprocess, tempfile, os

app = Flask(__name__)
env = Environment(loader=FileSystemLoader('templates'))

@app.route('/generate', methods=['POST'])
def generate_pdf():
    # 1) Parse JSON
    data = request.get_json()
    if not data:
        abort(400, "JSON body required")

    # 2) Compute grand_total
    items = data.get('items', [])
    try:
        grand = sum(
            float(item['quantity']) * float(item['unitPrice'])
            for item in items
        )
    except (KeyError, TypeError, ValueError):
        abort(400, "Each item needs numeric 'quantity' and 'unitPrice'")
    data['grand_total'] = grand

    # 3) Render template
    tpl = env.get_template('invoice.tex.j2')
    tex = tpl.render(invoice=data)

    # 4) Run pdflatex
    with tempfile.TemporaryDirectory() as tmp:
        tex_path = os.path.join(tmp, 'invoice.tex')
        with open(tex_path, 'w') as f:
            f.write(tex)

        subprocess.run(
            ['pdflatex', '-halt-on-error', 'invoice.tex'],
            cwd=tmp, check=True,
            stdout=subprocess.DEVNULL, stderr=subprocess.STDOUT
        )

        pdf_path = os.path.join(tmp, 'invoice.pdf')
        return send_file(
            pdf_path,
            mimetype='application/pdf',
            as_attachment=False,
            download_name='invoice.pdf'
        )

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)