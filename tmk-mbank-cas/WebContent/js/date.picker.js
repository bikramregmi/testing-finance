function fixDate() {
	var selectedMonth = document.getElementById("month").selectedIndex;
	var day = document.getElementById("day")
	var yearOptions = document.getElementById("year").options;
	var index = document.getElementById("year").selectedIndex;
	var selectedYear = yearOptions[index].text;
	if (selectedMonth == 1) {
		if (isLeapYear(selectedYear)) {
			setForFebLeapYear();
		} else {
			setForFeb();
		}

	}

	else if (selectedMonth == 0 || selectedMonth == 2 || selectedMonth == 4
			|| selectedMonth == 6 || selectedMonth == 7 || selectedMonth == 9
			|| selectedMonth == 11) {
		setForDay31();
	}

	else {
		setForDay30();
	}

	function isLeapYear(y) {

		if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	function setForFeb() {
		day.remove(30);
		day.remove(29);
		day.remove(28);
	}

	function setForFebLeapYear() {
		if (day.length == 28) {
			var option1 = document.createElement("option");
			option1.text = "29"
			day.add(option1);
		} else {
			day.remove(30);
			day.remove(29);
		}
	}

	function setForDay30() {
		if (day.length == 28) {
			var option1 = document.createElement("option");
			option1.text = "29"
			day.add(option1);
			var option2 = document.createElement("option");
			option2.text = "30";
			day.add(option2);
		} else if (day.length == 29) {
			var option1 = document.createElement("option");
			option1.text = "30"
			day.add(option1);
		} else if (day.length == 31) {
			day.remove(30);
		}
	}

	function setForDay31() {
		if (day.length == 28) {
			var option1 = document.createElement("option");
			option1.text = "29"
			day.add(option1);

			var option2 = document.createElement("option");
			option2.text = "30";
			day.add(option2);

			var option3 = document.createElement("option");
			option3.text = "31"
			day.add(option3);
		} else if (day.length == 29) {
			var option1 = document.createElement("option");
			option1.text = "30"
			day.add(option1);

			var option3 = document.createElement("option");
			option3.text = "31"
			day.add(option3);
		}

		else if (day.length == 30) {
			var option = document.createElement("option");
			option.text = "31";
			day.add(option);
		}
	}
}