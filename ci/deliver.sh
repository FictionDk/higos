#!/usr/bin/expect -f
set timeout 3000

set start_path [lindex $argv 0] 
set end_path [lindex $argv 1]
set secret_key [lindex $argv 2]
set remote_user [lindex $argv 3]
set remote_ip [lindex $argv 4]

spawn scp $start_path $remote_user@$remote_ip:~/$end_path
expect {
    "*yes/no*?" {
        send "yes\n"
        expect "*ssword:" { send "$secret_key\n" }
    }
    "*assword:" {
        send "$secret_key\n"
    }
}
expect "100%"

expect eof

