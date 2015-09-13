# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"

  config.vm.provider "virtualbox" do |v|
    v.name = "owl-links-trusty64"
    v.customize ["modifyvm", :id, "--cpuexecutioncap", "75"]
    v.memory = 6144
  end

  config.vm.provision :shell, :privileged => false, :path => "vagrant-machine-setup.sh"
  config.vm.provision :shell, :privileged => false, :path => "vagrant-machine-run.sh",run: "always"

  config.vm.network :forwarded_port, guest: 9000, host: 9000
  config.vm.network :forwarded_port, guest: 9999, host: 9999

  config.vm.synced_folder "../owl-links", "/owl-links"

end
