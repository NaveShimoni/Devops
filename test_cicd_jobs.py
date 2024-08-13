import pytest
import requests
import logging
from bs4 import BeautifulSoup

# Configure Logger
logging.basicConfig(
    level=logging.DEBUG,
    format='%(asctime)s %(levelname)s %(message)s',
    datefmt='%Y-%m-%d %H:%M:%S'
)
logger = logging.getLogger(__name__)

# Utility function to log HTTP responses
def log_http_response(response):
    logger.info(f"Status Code: {response.status_code}")
    content_type = response.headers.get('Content-Type', '')

    if 'application/json' in content_type:
        try:
            response_json = response.json()
            logger.info(f"Response Body: {response_json}")
        except ValueError:
            logger.error("Failed to parse JSON response")
    else:
        logger.warning("Response is not in JSON format")
        logger.info(f"Response Text: {response.text}")

# Function to extract CSRF token from the login page
def get_csrf_token(session, login_url):
    response = session.get(login_url)
    soup = BeautifulSoup(response.text, 'html.parser')
    csrf_token = soup.find('input', {'name': '_csrf'}).get('value')
    return csrf_token

# Fixtures
@pytest.fixture(scope='module')
def session():
    session = requests.Session()
    login_url = "http://localhost:8080/login"
    
    # Get the CSRF token
    csrf_token = get_csrf_token(session, login_url)
    
    credentials = {
        "username": "admin",
        "password": "admin",
        "_csrf": csrf_token  # Include the CSRF token in the login request
    }
    response = session.post(login_url, data=credentials)
    log_http_response(response)
    assert response.status_code == 200
    return session

@pytest.fixture(scope='module')
def base_url():
    return "http://localhost:8080/jobs"

# Test functions
def test_get_all_jobs(session, base_url):
    response = session.get(base_url)
    logger.info("Testing: Get All Jobs")
    log_http_response(response)
    assert response.status_code == 200

def test_create_job(session, base_url):
    job_data = {
        "jobName": "New Test Job",
        "status": "NEW",
        "jobType": "BUILD"
    }
    # Extract CSRF token again if required
    csrf_token = get_csrf_token(session, "http://localhost:8080/login")
    headers = {
        'X-CSRF-Token': csrf_token
    }
    response = session.post(base_url, json=job_data, headers=headers)
    logger.info("Testing: Create Job")
    log_http_response(response)
    assert response.status_code == 200

def test_get_job_by_id(session, base_url):
    job_id = 1
    response = session.get(f"{base_url}/{job_id}")
    logger.info("Testing: Get Job By ID")
    log_http_response(response)
    assert response.status_code == 200

def test_update_job_status(session, base_url):
    job_id = 1
    updated_status = {
        "status": "IN_PROGRESS"
    }
    # Extract CSRF token again if required
    csrf_token = get_csrf_token(session, "http://localhost:8080/login")
    headers = {
        'X-CSRF-Token': csrf_token
    }
    response = session.put(f"{base_url}/{job_id}", json=updated_status, headers=headers)
    logger.info("Testing: Update Job Status")
    log_http_response(response)
    assert response.status_code == 200

def test_delete_job(session, base_url):
    job_id = 1
    # Extract CSRF token again if required
    csrf_token = get_csrf_token(session, "http://localhost:8080/login")
    headers = {
        'X-CSRF-Token': csrf_token
    }
    response = session.delete(f"{base_url}/{job_id}", headers=headers)
    logger.info("Testing: Delete Job")
    log_http_response(response)
    assert response.status_code == 200


if __name__ == "__main__":
    pytest.main()
