
# Page Numbers Reducer API
The project is realized an API for getting reduced print pages list.


#### Parameters

```http
  GET /api/v1/reducedPageNumbers?rawPageNumbers=${pages}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `rawPageNumbers` | `string` | **Required**. List of comma separated page numbers |

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

- JDK 11+
- Maven 2.7.12

## Run Locally
Firstly, you need to start [Windows PowerShell](https://www.techtarget.com/searchwindowsserver/definition/PowerShell#:~:text=PowerShell%20is%20an%20object%2Doriented,systems%20and%20automate%20administrative%20tasks.).
<br />
**Check maven** is installed locally

```windows
  mvn -version
```
_If not, look at [instruction](https://phoenixnap.com/kb/install-maven-windows) of maven installation_

Check that the java 11 is [istalled](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/windows-7-install.html) locally as well.
```windows
  java -version
```

**Clone** or download [zip](https://github.com/MikeLogvinov/PrintPagesReducerApp/archive/refs/heads/master.zip) of the project

```windows
  git clone https://github.com/MikeLogvinov/PrintPagesReducerApp.git
```

Go to the **project directory**

```windows
  cd path_to_the_cloned_or_unziped_folder_PrintPagesReducerApp
```

**Install dependencies**

```windows
  mvn clean install
```

**Run project**
```windows
  mvn spring-boot:run
```
The application will be available at http://localhost:8080/api/v1/reducedPageNumbers?rawPageNumbers=
<br /><br />**Demo**<br />
![pagereducer.gif](assets%2Fimages%2Fpagereducer.gif)

## Docker Deployment

If the "Run Locally" part is completed successfully start [Docker Desctop](https://docs.docker.com/desktop/install/windows-install/).
<br/>Go to the **project directory**

```windows
  cd path_to_the_folder_PrintPagesReducerApp
```
**Create image**

```windows
  docker build -t printpagereducer .
```
**Run container**
```windows
  docker run -d -p 9090:8080 --name printpagereducer printpagereducer
```

Web page should be accessible on http://localhost:9090/api/v1/reducedPageNumbers?rawPageNumbers=

## Documentation the API using OpenAPI 3.0:
**Run project** on server
```windows
  mvn spring-boot:run
```
Open a link  < http://... >/swagger-ui/index.html
<br /><br />Demo<br />
![swagger_pagereducer.png](assets%2Fimages%2Fswagger_pagereducer.png)
## Tech Stack

**Client:** Web Browser

**Server:** Apache Tomcat/9.0.75, Spring Boot 2.7.12, Java 11, Swagger, Maven 2.7.12

## Author

[@MikeLogvinov](https://github.com/MikeLogvinov)