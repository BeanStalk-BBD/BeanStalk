terraform {
  backend "s3" {
    bucket = "beanstalkbucket"
    key = "beanstalkbucket/terraform.tfstate"  # Specify the path/key for your state file
    region = "eu-west-1"
  }
}