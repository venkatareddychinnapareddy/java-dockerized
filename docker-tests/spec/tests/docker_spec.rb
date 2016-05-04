require "spec_helper"

describe "Java-dockerized Docker image" do
  before(:all) do
    set :os, family: :debian
    set :backend, :docker
    set :docker_image, image
  end

  it "installs the right version of java" do
    os_version = command("dpkg -l openjdk-8-jre").stdout
    expect(os_version).to include(" 8u")
  end

  describe file('/usr/bin/java') do
    it { should exist }
    it { should be_symlink }
  end

  describe command('/usr/bin/java -version') do
    its(:stderr) { should match /openjdk version "1\.8/ }
  end

  describe file('/app/java_project/dockerized-0.0.1-SNAPSHOT.jar') do
    it { should exist }
    it { should be_file }
    it { should be_readable }
  end

  describe file('/app/java_project/log4j2.xml') do
    it { should exist }
    it { should be_file }
    it { should be_readable }
  end
  
end
