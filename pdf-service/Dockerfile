# FROM python:3.11-slim
# RUN apt-get update \
#  && apt-get install -y --no-install-recommends texlive-latex-base \
#  && rm -rf /var/lib/apt/lists/*
# WORKDIR /app
# COPY requirements.txt .
# RUN pip install --no-cache-dir -r requirements.txt
# COPY . .
# EXPOSE 5000
# CMD ["python","app.py"]


FROM python:3.11-slim
RUN apt-get update \
 && apt-get install -y --no-install-recommends texlive-latex-base \
 && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY . .
EXPOSE 5000
# Change the command to use Gunicorn for production, but keep existing structure:
CMD ["gunicorn", "app:app", "--bind", "0.0.0.0:5000"]