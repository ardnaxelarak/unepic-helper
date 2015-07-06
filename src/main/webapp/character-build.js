var level;
var Dataset;

function handle_ajax_error(jqx, status, errMsg) {
    console.log('Ajax error: ' + jqx.status + ' ' + status + ' ' + errMsg);
}

function load_dataset(name) {
    var onSuccess = function(data) {
        Dataset = data;
        init_allocation_body();
        updateTotal();
    }

    $.ajax({
        type: "GET",
        url: "data?profile=" + name,
        dataType: "json",
        success: onSuccess,
        error: handle_ajax_error
    });
}

$(document).ready(function() {
    level = 5;
    load_dataset("multiplayer");
});

function init_allocation_body() {
    $body = $('.allocation_body');
    $('.allocation_row:not(.template)', $body).remove();
    var catlen = Dataset.categories.length;
    var len;
    for (var i = 0; i < catlen; i++) {
        len = Dataset.categories[i].skills.length;
        for (var j = 0; j < len; j++) {
            $body.append(make_skill_tr(Dataset.categories[i].skills[j]));
        }
    }
    $('.template', $body).hide();

    $(".constitution_benefits_div").append('<span class="hp_total"></span> hp');
}

function make_skill_tr(skill) {
    var info = Dataset.info[skill];
    var $x = $('.allocation_row.template').clone();
    $x.removeClass('template');
    $('.display_name', $x).text(info.display);
    $('.minus_btn', $x).attr('onclick', "minusClicked('" + skill + "');");
    $('.plus_btn', $x).attr('onclick', "plusClicked('" + skill + "');");
    $('.skill_value', $x).addClass(skill + "_value");
    $('.skill_value', $x).text(info.start);
    $('.skill_value', $x).attr('skill_name', skill);
    $('.benefits_div', $x).addClass(skill + "_benefits_div");
    $x.addClass(skill + '_allocation_row');

    var len = 0;
    if (info.entries)
        len = info.entries.length;
    var $bendiv = $('.benefits_div', $x);
    for (var i = 0; i < len; i++) {
        if (!info.entries[i].src)
            info.entries[i].src = "images/" + info.entries[i].type + "/" + info.entries[i].name + ".png";
        $bendiv.append(make_entry_item(info.entries[i]));
    }

    update_benefits(info.start, $x);
    return $x;
}

function make_entry_item(entry) {
    var $x = $('<span class="entry_item" entry_level="0"><img src="" title="" class="entry_icon"></span>');
    $('.entry_icon', $x).attr('src', entry.src);
    $('.entry_icon', $x).attr('title', entry.display);
    $x.attr('entry_level', entry.level);
    return $x;
}

function update_benefits(value, $container) {
    $('.entry_item', $container).each(function() {
        var req_level = parseInt($(this).attr('entry_level'));
        if (req_level > value)
            $(this).hide();
        else
            $(this).show();
    });
}

function set_value(id, value, update)
{
    update = (typeof update !== 'undefined') ?  update : true;
    $container = $('.' + id + '_allocation_row');
    $('.skill_value', $container).text(value);
    update_benefits(value, $container);
    if (update)
        updateTotal();
}

function plusClicked(id) {
    var tag = "." + id + "_value"
    var value = parseInt($(tag).text());
    if (value < level) {
        set_value(id, value + 1);
    }
}

function minusClicked(id) {
    var tag = "." + id + "_value"
    var value = parseInt($(tag).text());
    if (value > Dataset.info[id].start) {
        set_value(id, value - 1);
    }
}

function levelMinusClicked() {
    if (level > 1) {
        level -= 1;
        $(".level_value").text(level);
        updateTotal();
    }
}

function levelPlusClicked() {
    if (level < 20) {
        level += 1;
        $(".level_value").text(level);
        updateTotal();
    }
}

function updateTotal() {
    var remaining = 5 + 6 * level;
    $(".allocation_row:not(.template) .skill_value").each(function() {
        var value = parseInt($(this).text());
        if (value > level) {
            $(this).text(level);
            value = level;
            update_benefits(value, $(this).parents('.allocation_row'));
        }
        remaining -= value;
    });
    var hptotal = 110 + 20 * level;
    $(".constitution_value").each(function() {
        var value = parseInt($(this).text());
        hptotal += 20 * value;
    });

    $(".remaining_points").text(remaining);
    $(".hp_total").text(hptotal);
}

function getBuildJson() {
    var X = {};

    $(".allocation_row:not(.template) .skill_value").each(function() {
        var value = parseInt($(this).text());
        var skill = $(this).attr('skill_name');
        X[skill] = value;
    });

    return JSON.stringify(X);
}

function saveBuild() {
    var onSuccess = function(data) {
    };

    $.ajax({
        type: "POST",
        url: "character/save?user=" + userId + "&character=default&level=" + level,
        data: getBuildJson(),
        contentType: "application/json; chaset=utf-8",
        dataType: "json",
        success: onSuccess,
        error: handle_ajax_error
    });
}
