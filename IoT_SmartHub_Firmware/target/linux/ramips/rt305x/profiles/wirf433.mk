#
# Copyright (C) 2013 OpenWrt.org
#
# This is free software, licensed under the GNU General Public License v2.
# See /LICENSE for more information.
#

define Profile/WIRF433
	NAME:=Irdroid WIRF433
endef

define Profile/WIRF433/Description
	Package set for Hardware Group Ltd. WIRF433 Module
endef

$(eval $(call Profile,WIRF433))

