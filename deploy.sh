#!/usr/bin/env bash
set -e

cd product-api

docker compose pull
docker compose up -d
