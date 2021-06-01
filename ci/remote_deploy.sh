#!/usr/bin/expect -f
set timeout 3000

set file_path [lindex $argv 0]
set secret_key [lindex $argv 1]
set remote_user [lindex $argv 2]
set remote_ip [lindex $argv 3]
set active [lindex $argv 4]

spawn ssh ${remote_user}@${remote_ip} "cd ~/$file_path; ls ; sh deploy.sh run $active"
expect {
    "*yes/no*?" {
        send "yes\n"
        expect "*ssword:" { send "$secret_key\n" }
    }
    "*assword:" {
        send "$secret_key\n"
    }
}
expect eof

