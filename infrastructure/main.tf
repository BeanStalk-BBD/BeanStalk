# Create a VPC
resource "aws_vpc" "example_vpc" {
  cidr_block = "172.30.0.0/16"
}

# Create Subnet 1
resource "aws_subnet" "subnet1" {
  vpc_id     = aws_vpc.example_vpc.id
  cidr_block = "172.30.0.0/24"
}

# Create Subnet 2
resource "aws_subnet" "subnet2" {
  vpc_id     = aws_vpc.example_vpc.id
  cidr_block = "172.30.1.0/24"
}


# Create Subnet 2
resource "aws_subnet" "subnet3" {
  vpc_id     = aws_vpc.example_vpc.id
  cidr_block = "172.30.2.0/24"
}

# Create a security group
resource "aws_security_group" "example_sg" {
  name        = "example-sg"
  description = "Example security group"

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 65535
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  vpc_id = aws_vpc.example_vpc.id
}


resource "aws_db_instance" "default" {
  allocated_storage = 20
  engine = "PostgreSQL"
  instance_class = "db.t3.micro"
 username = jsondecode(data.aws_secretsmanager_secret_version.creds.secret_string)["username"]
  password = jsondecode(data.aws_secretsmanager_secret_version.creds.secret_string)["password"]
  skip_final_snapshot = true // required to destroy
  publicly_accessible= true
  identifier = "beanstalk"
}
