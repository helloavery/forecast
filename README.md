# Forecaster

An analytical dashboard for supply chain forecasting

Currently solely developed and maintained by Avery Grimes-Farrow (Avery Grimes)

Based on Kickstarter data (Product Demand and Product Forecast). [You can grab the data from here][forecastdata] (under 'Project - Forecaster')

# Structure

* This is a backend application.
* Language : Java 8
* DB : MongoDB (4.2.0)
* Uses my [S3 Gateway Project][s3gateway] in order to retrieve and push secrets
* Uses Kafka ([Notification Manager Project][notification-manager]) in order to send events such as e-mail notifications
* Uses my Service Discovery project (Open Feign) for REST related logic
* Twilio Authy verifies tokens at account creation as well issues one-time passwords for account login
* Mailgun (transactional API) handles sending e-mails, as well as verifying valid e-mail addresses

[forecastdata]: https://averygrimes.com/experience/experience#softwareengineer
[reladomohomepage]: https://github.com/goldmansachs/reladomo
[s3gateway]: https://github.com/helloavery/s3-gateway
[notification-manager]: https://github.com/helloavery/notification-manager

# Timeline
I do not have a specific timeline. This is a project that I'm working as a go in my free time. 
Though, I would like to get somewhat of an alpha working sometime by January/February


This README is still a work-in-progress
