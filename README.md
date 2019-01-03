# Forecaster

An analytical dashboard for supply chain forecasting

Currently solely developed and maintained by Avery Grimes-Farrow (Avery Grimes)

Based on Kickstarter data (Product Demand and Product Forecast). [You can grab the data from here][forecastdata]

# Structure

* This is the backend application. There is a separate project for the UI which utilizes Angular 6 CLI
* Language : Java 8
* DB : Postgres (10.6) 
* ORM : [Goldman Sachs Reladomo][reladomohomepage]
* Uses my [S3 Gateway Project][s3gateway] in order to retrieve and push secrets
* Twilio Authy verifies tokens at account creation as well issues one-time passwords for account login
* Mailgun (transactional API) handles sending e-mails, as well as verifying valid e-mail addresses
* Possibly might incorporate R

[forecastdata]: https://averygrimes.com/experience/experience#dataanalysis
[reladomohomepage]: https://github.com/goldmansachs/reladomo
[s3gateway]: https://github.com/helloavery/s3-gateway

# Timeline
I do not have a specific timeline. This is a project that I'm working as a go in my free time. 
Though, I would like to get somewhat of an alpha working sometime by January/February


This README is still a work-in-progress
