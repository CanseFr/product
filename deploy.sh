#!/usr/bin/env bash
set -e

cd /root/product-api

docker compose pull
docker compose up -d
