Protobuf Start Learning
=======================

### Install Protobuf Generate Plugin
```sh
    brew install protobuf
```

### Generate Code
```sh
    protoc -I=. --java_out=src/main/java src/main/proto/contacts.proto
```
