#!/bin/sh


# Helper function to trim \r\n from secrets
read_secret() {
  cat "/run/secrets/$1" | tr -d '\r\n'
}

export DB_URL=$(read_secret DB_URL)
export DB_USER=$(read_secret DB_USER)
export DB_PASS=$(read_secret DB_PASS)

export ADMIN_USERNAME=$(read_secret ADMIN_USERNAME)
export ADMIN_EMAIL=$(read_secret ADMIN_EMAIL)
export ADMIN_PASSWORD=$(read_secret ADMIN_PASSWORD)

export CLOUDINARY_CLOUD_NAME=$(read_secret CLOUDINARY_CLOUD_NAME)
export CLOUDINARY_API_KEY=$(read_secret CLOUDINARY_API_KEY)
export CLOUDINARY_API_SECRET=$(read_secret CLOUDINARY_API_SECRET)

export MAIL_USERNAME=$(read_secret MAIL_USERNAME)
export MAIL_PASS=$(read_secret MAIL_PASS)

export STRIPE_SECRET=$(read_secret STRIPE_SECRET)
export WEBHOOK_SECRET=$(read_secret WEBHOOK_SECRET)

export GOOGLE_CLIENT_ID=$(read_secret GOOGLE_CLIENT_ID)
export GOOGLE_CLIENT_SECRET=$(read_secret GOOGLE_CLIENT_SECRET)
export GOOGLE_SCOPES=$(read_secret GOOGLE_SCOPES)


# Start Spring Boot app
exec java -jar app.jar
