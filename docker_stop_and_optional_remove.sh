REMOVE_IMAGE=false
if [ "$1" == "--rmi" ]; then
  REMOVE_IMAGE=true
fi


if docker ps | grep -q "stockexchange_container"; then
	echo "Container 'stockexchange_container' is running. Stopping it."
    docker stop stockexchange_container
    docker rm stockexchange_container
fi

# If --rmi is passed, remove existing image
if $REMOVE_IMAGE; then
	if docker images -a | grep -q "stockexchange"; then
	  echo "Removing existing image 'stockexchange'."
      docker rmi stockexchange
	else
		echo "Image 'stockexchange' does not exists "
	fi
fi
