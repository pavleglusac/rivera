# Rivera

## Description

An in-depth paragraph about your project and overview of use.

## Getting Started

### Dependencies

JDK 17
Node.js 16.14.2 LTS
PostgreSQL 14

### Running in docker

To run Rivera on docker, execute the following commands while in base directory 

```
docker-compose build
docker-compose up
```

To ensure reasonable performance on Windows, use this .wslconfig and save it in C:/Users/Username/.wslconfig

```
[wsl2]
memory=900MB
processors=1
```
### Running locally
While in base directory, execute the following command
* Windows:
```
./run.bat
```
* Linux:
```
./run.sh
```

Or, manually:
1. While in /Rivera/backend/rivera
```
mvn spring-boot:run
```
2. While in /Rivera/frontend/river
```
npm i
npm run dev
```

## Help



## Authors

* [Pavle Glušac]()
* [Anđela Mišković]()
* [Dejan Dopuđ]()

## Version History

* 0.1
    * Initial Release

## License


## Acknowledgments
