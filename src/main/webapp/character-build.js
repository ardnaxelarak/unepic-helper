var Skills = {
    'swords': {'min': 1},
    'daggers': {'min': 1},
    'polearms': {'min': 1},
    'axes': {'min': 1},
    'maces': {'min': 1},
    'bows': {'min': 1},
    'staves': {'min': 1},
    'wands': {'min': 1},

    'constitution': {'min': 1},
    'armor': {'min': 1},
    'robes': {'min': 1},

    'fire': {'min': 0},
    'frost': {'min': 0},
    'healing': {'min': 0},
    'arcane': {'min': 0},
    'light': {'min': 0},
    'mental': {'min': 0},
    'alteration': {'min': 0},
    'protection': {'min': 0}
}

var level;

$(document).ready(function() {
    level = 5;
    $.each(Skills, function(key, value) {
        console.log(key + ": " + value.min);
        $("#" + key + "-value").text(value.min);
    });
    $("#constitution-benefits").append("<p id=hp-total></p>");
    updateTotal();
});

function plusClicked(id) {
    var tag = "#" + id + "-value"
    var value = parseInt($(tag).text());
    if (value < level) {
        $(tag).text(value + 1);
        updateTotal();
    }
}

function minusClicked(id) {
    var tag = "#" + id + "-value"
    var value = parseInt($(tag).text());
    if (value > Skills[id].min) {
        $(tag).text(value - 1);
        updateTotal();
    }
}

function levelMinusClicked() {
    if (level > 1) {
        level -= 1;
        $("#level-value").text(level);
        updateTotal();
    }
}

function levelPlusClicked() {
    if (level < 20) {
        level += 1;
        $("#level-value").text(level);
        updateTotal();
    }
}

function updateTotal() {
    var remaining = 5 + 6 * level;
    $("#allocation-div a[id$='value']").each(function() {
        var value = parseInt($(this).text());
        if (value > level) {
            $(this).text(level);
            value = level;
        }
        remaining -= value;
    });
    var hptotal = 110 + 20 * level;
    $("#constitution-value").each(function() {
        var value = parseInt($(this).text());
        hptotal += 20 * value;
    });

    $("#remaining-value").text(remaining);
    $("#hp-total").text(hptotal + " hp");
}
