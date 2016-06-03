#! /bin/bash

export AARCH64_CC=aarch64-linux-gnu-gcc
export AARCH64_LD=aarch64-linux-gnu-ld
export AARCH64_AR=aarch64-linux-gnu-ar
export AARCH64_OBJDUMP=aarch64-linux-gnu-objdump

export AARCH32_CC=arm-linux-gnueabi-gcc
export AARCH32_LD=arm-linux-gnueabi-ld
export AARCH32_AR=arm-linux-gnueabi-ar
export AARCH32_OBJDUMP=arm-linux-gnueabi-objdump

export ODP_SDK=$PWD

echo "ODP_SDK exported as $ODP_SDK"


quit()
{
	QUIT=$1
}


uninstall_targets()
{
	make uninstall
}


step1_func()
{
	TITLE="Select the ODP build"

	TEXT[1]="build pv660 odp"
	FUNC[1]="./scripts/odp_build.sh"

#	TEXT[2]="build pv660 drive"
#	FUNC[2]="./scripts/drv_build.sh"
}


step2_func()
{
	TITLE="Setup linuxapp environment"

}


step3_func()
{
	TITLE="build test application"

	TEXT[1]="build test application"
	FUNC[1]="./scripts/app_build.sh"

	TEXT[2]="build test validation"
	FUNC[2]="./scripts/test_build.sh"
}


step4_func()
{
	TITLE="Other tools"
	TEXT[1]="Copy files to a board"
	FUNC[1]="./copy.sh"
}


step5_func()
{
	TITLE="Uninstall and system cleanup"
}

STEPS[1]="step1_func"
STEPS[2]="step2_func"
STEPS[3]="step3_func"
STEPS[4]="step4_func"
STEPS[5]="step5_func"

QUIT=0


while [ "$QUIT" == "0" ]; do
	OPTION_NUM=1

	echo ""
	echo -e "\033[31;1m----------------------------------------------------------\033[0m"
	for s in $(seq ${#STEPS[@]}) ; do
		${STEPS[s]}

		echo -e "\033[40;34;1mStep $s: ${TITLE} \033[0m"

		for i in $(seq ${#TEXT[@]}) ; do
			echo "  [$OPTION_NUM] ${TEXT[i]}"
			OPTIONS[$OPTION_NUM]=${FUNC[i]}
			let "OPTION_NUM+=1"
		done

		# Clear TEXT and FUNC arrays before next step
		unset TEXT
		unset FUNC

		echo ""
	done

	echo "  [$OPTION_NUM] Exit Script"
	OPTIONS[$OPTION_NUM]="quit"
	echo ""
	echo -n "Option: "
	read our_entry
	echo ""
	${OPTIONS[our_entry]} ${our_entry}
done
