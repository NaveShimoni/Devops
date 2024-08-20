FROM python:3.9-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY test_cicd_jobs.py /app/

CMD ["pytest", "test_cicd_jobs.py"]
