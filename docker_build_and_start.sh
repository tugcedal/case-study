if docker ps | grep -q "stockexchange_container"; then
	echo "Container 'stockexchange_container' is running. Stopping and removing it."
    docker stop stockexchange_container
    docker rm stockexchange_container
fi

if docker images | grep -q "stockexchange"; then
	echo "Docker image 'stockexchange' exist. Reusing the image."

else
	echo "Docker image 'stockexchange' does not exist. Building the image."
	docker build -t stockexchange .
fi


echo "Running the Docker container 'stockexchange_container'."
docker run -d -p 8080:8080 --name stockexchange_container stockexchange
	  
