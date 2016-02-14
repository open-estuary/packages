#!/bin/bash
#author: fajun yang
#date: 6/2/2016
#description: postinstall scripts for Armor Tools

###################### remove odp driver ###########################

echo "rmmod uio kernel driver..."
rmmod uio_enet_drv


echo "remove finished!"
