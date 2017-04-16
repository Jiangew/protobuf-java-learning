## Protobuf Start Learning ##


### Install Protobuf Generate Plugin ###
brew install protobuf

### Generate Code ###
protoc -I=. --java_out=src/main/java src/main/proto/contacts.proto

