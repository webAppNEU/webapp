variable "aws_region" {
  type    = string
  default = "us-east-1"
}
variable "source_ami" {
  type    = string
  default = "ami-0dfcb1ef8550277af" # Amazon Linux 2 AMI (HVM) - Kernel 5.10, SSD Volume Type
}
variable "ssh_username" {
  type    = string
  default = "ec2-user"
}

variable "subnet_id" {
  type    = string
  default = "subnet-018148a11e84fb2c0"
}
// variable "profile" {
//   type    = string
//   default = "dev"
// }

# https://www.packer.io/plugins/builders/amazon/ebs
source "amazon-ebs" "my-ami" {
  region          = "us-east-1"
  ami_name        = "csye6225_${formatdate("YYYY_MM_DD_hh_mm_ss", timestamp())}"
  ami_description = "AMI for CSYE 6225"
  #  profile         = "${var.profile}"
  

  ami_regions = [
    "us-east-1",
  ]



  // aws_polling {
  //   delay_seconds = 120
  //   max_attempts  = 50
  // }

  associate_public_ip_address = true
  instance_type               = "t2.micro"
  source_ami                  = "${var.source_ami}"
  subnet_id                   = "${var.subnet_id}"

  // ssh_handshake_attempts = 200
  #security_group_id      = "sg-0acb65bd8a41f9767"
  #ssh_keypair_name       = "ec2test"
  #ssh_private_key_file   = "~/.ssh/ec2test"

  launch_block_device_mappings {
    delete_on_termination = true
    device_name           = "/dev/xvda"
    volume_size           = 8
    volume_type           = "gp2"
  }

  ssh_username = "${var.ssh_username}"

}
build {
  sources = ["source.amazon-ebs.my-ami"]

provisioner "shell" {
    environment_vars = [
      "DEBIAN_FRONTEND=noninteractive",
      "CHECKPOINT_DISABLE=1"
    ]
    inline = [
      "mkdir /home/ec2-user/webapp",
    ]
  }
//  post-processor "manifest" {
//     output = "/home/runner/work/webapp/manifest.json"
//     strip_path = true
//     custom_data = {
//       my_custom_data = "example"
//     }
// }

  // provisioner "file" {
  //   source      = "/home/runner/work/webapp/webapp/"
  //   destination = "/home/ec2-user/webapp"
  // }
    provisioner "file" {
    source      = "/home/runner/work/webapp/webapp/target/"
    destination = "/home/ec2-user/webapp"
  }

      provisioner "file" {
    source      = "./webapp.service"
    destination = "/home/ec2-user/webapp/"
  }

  provisioner "shell" {
    environment_vars = [
      "DEBIAN_FRONTEND=noninteractive",
      "CHECKPOINT_DISABLE=1"
    ]
    script       = "./buildandrun.sh"
    pause_before = "5s"
  }
  provisioner "shell" {
    environment_vars = [
      "DEBIAN_FRONTEND=noninteractive",
      "CHECKPOINT_DISABLE=1"
    ]
    inline = [
      ""AMI="$(curl http://169.254.169.254/latest/meta-data/ami-id)""",
      ""aws ec2 modify-image-attribute 
    \ --image-id $AMI 
    \ --launch-permission "Add=[{UserId=180918132071}]" --region us-east-1"",
     
    ]
  }

}
