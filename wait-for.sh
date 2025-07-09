#!/bin/bash
set -e

host="keycloak"
port=8088
realm="my-chat-realm"
url="http://$host:$port/realms/$realm"

echo "Waiting for Keycloak realm $realm at $url..."

until curl -sf "$url" > /dev/null; do
  echo "Keycloak not ready, waiting..."
  sleep 3
done

echo "Keycloak is ready, starting gate..."
exec "$@"
