# golang-learning-1
golang learning



## Developer Guide


**Golang Version Manager and Installer**    
https://github.com/syndbg/goenv


### Setting up Development Machine

#### Install the build-essential package.
```
sudo apt-get install build-essential
```

#### Installing Goenv (Golang Version Manager)
Follow instruction on https://github.com/syndbg/goenv#installation
```
# Install Golang 1.9.4
goenv install 1.9.4

# Use golang version 1.9.4
goenv local 1.9.4

# Verify that it is correct version
go version
```

#### Set up your GO workspace
Create the go path directory:
```
mkdir ~/go
```

Add the following to your `~/bashrc` file:
```
export GOPATH=$HOME/go
export PATH=$PATH:$GOPATH/bin
export PATH=$PATH:/usr/local/go/bin
# ulimit -n 8096
```

Reload your bash configuration:
```
source ~/.bashrc
```

Create the directory for code
```
mkdir -p ~/go/src/github.com/mannyrivera2010
cd ~/go/src/github.com/mannyrivera2010
git clone git@github.com:mannyrivera2010/golang-learning-1.git
```

### Compiling Test Project
This project uses GNU Make to control
- generation of build
- run the test suite
- manage build environment

#### Useful make commands
`make run`    
run code

`make test`    
run test

### Running this test Project
To run changes to server-side code:
```
make stop
make test
make run
```

To clean the database:
```
make stop
make clean-docker
```

To clean the database, logs, and workspace:
```
make stop
make clean
```

To remove everything:
```
make stop
make nuke
```

To check styles of Go
```
make check-style
```

To run a test SMTP server using Python:
```
make debug-smtp
```

### Style Guides
- All go code must follow the golang official Style Guide
- In addition all code must be run though the official go formatter tool gofmt

## Admin Guide
