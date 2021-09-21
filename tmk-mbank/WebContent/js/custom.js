/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** ****** left menu *********************** * */
//by ros
/*$(document).on('change', '.jqusersel', function() {
    $(this).attr('numcounter',
        parseInt($(this).attr('numcounter')) + 1);
    $('.userObject').val("");
    $('.userObject').css('background-color', '#fff');
    $('.returncusdata').empty();
    
   if(($(this).val()=="Bank")){
		$('.usertemp').show();
   }else{
	    $('.usertemp').hide();
   }
    
});*/

$(document).on('click', '.newtr', function(e) {
    $('.objectUserId').val($(this).find('td:first').text());
    $('.userObject').val($(":nth-child(2)", this).text());
    $('.userObject').css('background-color', '#9ACD32');
    $('.returncusdata').empty();
    $('.userObject').attr('status','1');
});

$(document).ready(function() {
    $('.userObject').on(
        'keyup', function() {
        	$('.userObject').css('background-color', '#fff');
        	$(this).attr('status', '0');
        var path = $(this).attr('thisUri');
        var dataInfo = {
            "firecounter": $(this).attr('firecounter'),
            "usertype": $('.jqusersel option:selected').val(),
            "objectName": $(this).val()
        };
        ajaxFunction(path, dataInfo, $(this), $(
            '.jqusersel').attr("numcounter"), $(
            '.jqusersel option:selected').val(),
            implimentation);
        $(this).attr('firecounter',
            parseInt($(this).attr('firecounter')) + 1);
    });
});

function implimentation(data, that, userType, numcounter) {
	console.log(data);
    if (data.status == 'ok') {
    	if(that.attr('status')=="0"){
	        if (that.attr('respcounter') <= data.response) {
	            that.attr('respcounter', data.response);
	            this.populateAdmin(data.details, numcounter);
	          /*  if (userType == "Super_Agent" || userType=="Agent") {
	               
	            }*/
	        }
    	}
    }
}

function populateAdmin(data, numcounter) {
	console.log(data);
    if (numcounter == $('.jqusersel').attr("numcounter")) {
        $('.returncusdata').empty();
        if (data.length > 0) {
            var currentEleth;
            var currentEletd;
            $('.returncusdata').append("<tr class='newtrhead'></tr>").after(function() {
                var ele = $('.newtrhead', this);
                currentEleth = (ele[ele.length - 1]);
            });
            var hs = 0;
            $.each(data, function() {
                $('.returncusdata').append("<tr class='newtr'></tr>").after(function() {
                    var ele = $('.newtr', this);
                    currentEletd = (ele[ele.length - 1]);
                });
                var start = 1;
                $.each(this, function(k, v) {
                    if (start < 6) {
                        if (hs == 0) {
                            $(currentEleth).append(("<td>" + k + "</td>"));
                        }
                        $(currentEletd).append("<td>" + v + "</td>");
                        start++;
                    }
                });
                hs = 1;
            });
        }
    }
}

function ajaxFunction(path, data, that, numcounter, userType, cb) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: path,
        data: data,
        dataType: 'json',
        timeout: 100000,
        success: function(data) {
            cb(data, that, userType, numcounter);
        },
        error: function(e) {
            console.log("ERROR: ", e);
            cb(e);
        },
        done: function(e) {
            console.log("DONE");
        }
    });

}
// by ros
$(document)
    .ready(function() {

    // addAgent section
    $("#agentSuperAgent")
        .change(function() {
        var agentSuperAgent = $(this).val();
        $
            .ajax({
            type: "GET",
            url: '/ajax/agent/country?name=' + agentSuperAgent,
            success: function(response) {
                $('#agentCountry')
                    .val(
                    response.isoThree);
                $
                    .ajax({
                    type: "GET",
                    url: '/ajax/agent/state?iso=' + response.isoThree,
                    success: function(
                        responseTwo) {
                        var len = responseTwo.length;
                        var options = '';
                        // console.log(response);
                        for (var i = 0; i < len; ++i) {
                            console
                                .log(responseTwo[i].firstName);
                            options += '<option value="' + responseTwo[i].id + '">' + responseTwo[i].name + '</option>';
                        }

                        $(
                            '#agentState')
                            .html(
                            options);
                    }

                });

            }

        });
    });

    $("#agentState").change(function() {
        var agentState = $(this).val();
        $.ajax({
            type: "GET",
            url: '/ajax/agent/city?stateName=' + agentState,
            success: function(response) {
                var len = response.length;
                var options = '';
                // console.log(response);
                for (var i = 0; i < len; ++i) {
                    console.log(response[i].firstName);
                    options += '<option value="' + response[i].name + '">' + response[i].name + '</option>';
                }

                $('#agentCity').html(options);

            }

        });
    });

    // transaction payout channel
    /*
     * $("#payoutCountry").change(function(){ var payoutCountry =
     * $(this).val(); var sendingSuperAgentId =
     * $('#sendingSuperAgentId').val(); var sendingAgentId =
     * $('#sendingAgentId').val(); var sendingCurrency =
     * $('#sendingCurrency').val(); $.ajax({ type: "GET", url:
     * '/ajax/transaction/beneficiary?iso='+payoutCountry,
     * success: function(response) { var len = response.length;
     * var options = ''; //console.log(response); for(var i=0; i<len;
     * ++i) { console.log(response[i].firstName); options += '<option
     * value="'+response[i].firstName + '">'
     * +response[i].firstName+ '</option>'; }
     * 
     * $('#beneficiary').html(options); }
     * 
     * }); $.ajax({ type: "GET", url:
     * '/ajax/transaction/exchangeRate?agentId='+sendingAgentId+"&superAgentId="+sendingSuperAgentId+"&iso="+payoutCountry+"&fromCurrency="+sendingCurrency,
     * success: function(response) {
     * 
     * $('#exchangeRate').val(response.buyingRate); }
     * 
     * }); });
     */

    $('#payoutSuperAgentId')
        .change(function() {
        var payoutSuperAgentId = $(this).val();

        $
            .ajax({
            type: "GET",
            url: '/ajax/transaction/receivingAgent?id=' + payoutSuperAgentId,
            success: function(response) {
                var len = response.length;
                var options = '';
                for (var i = 0; i < len; ++i) {
                    options += '<option value="' + response[i].id + '">' + response[i].firstName + '</option>';
                }
                $('#payoutAgentId')
                    .html(options);
            }
        });

    });

    $("#sendingAmount")
        .change(function() {
        var sendAgentId = $(
            '#sendingSuperAgentId').val();
        var receiveAgentId = $(
            '#payoutSuperAgentId').val();
        var sendAmount = $('#sendingAmount')
            .val();
        var exchangeRate = $('#exchangeRate')
            .val();
        var commissionType = $("#payoutChannel")
            .val();
        if (commissionType == "agent") {
            $
                .ajax({
                type: "GET",
                url: '/ajax/transaction/commission?sendAgentId=' + sendAgentId + "&receiveAgentId=" + receiveAgentId + "&sendAmount=" + sendAmount + "&commissionType=" + commissionType,
                success: function(
                    response) {

                    $(
                        '#sendingCommission')
                        .val(
                        response.overall_commission_flat);

                    $(
                        '#totalSendingAmount')
                        .val(+sendAmount + +(response.overall_commission_flat));

                    $(
                        '#receivingAmount')
                        .val(
                        sendAmount * exchangeRate);
                }
            });
        } else {
            $
                .ajax({
                type: "GET",
                url: '/ajax/transaction/commission?sendAgentId=' + sendAgentId + "&receiveAgentId=" + receiveAgentId + "&sendAmount=" + sendAmount + "&commissionType=" + commissionType,
                success: function(
                    response) {

                    $('#sendingCommission').Value = response.overall_commissionFlat;
                    $('#totalSendingAmount').Value = +sendAmount + +response.overall_commissionFlat;
                    $('#receivingAmount').Value = sendAmount * exchangeRate;
                }
            });
        }
    });

    $("#payoutChannel")
        .change(function() {
        var payoutCountry = $('#payoutCountry')
            .val();
        var payoutChannel = $(this).val();
        if (payoutChannel == "agent") {
            $
                .ajax({
                type: "GET",
                url: '/ajax/transaction/recevingSuperAgent?iso=' + payoutCountry,
                success: function(
                    response) {
                    var len = response.length;
                    var options = '';
                    // console.log(response);
                    for (var i = 0; i < len; ++i) {
                        console
                            .log(response[i].firstName);
                        options += '<option value="' + response[i].id + '">' + response[i].firstName + '</option>';
                    }

                    $(
                        '#payoutSuperAgentId')
                        .html(
                        options);

                }

            });

        } else if (payoutChannel == "bankAccount") {
            $
                .ajax({
                type: "GET",
                url: '/ajax/transaction/bank?iso=' + payoutCountry,
                success: function(
                    response) {
                    var len = response.length;
                    var options = '';
                    // console.log(response);
                    for (var i = 0; i < len; ++i) {
                        console
                            .log(response[i].name);
                        options += '<option value="' + response[i].id + '">' + response[i].name + '</option>';
                    }

                    $('#bankId').html(
                        options);

                }

            });
            // GET branchId from bankId
            $('#bankId')
                .change(function() {
                var bankId = $(
                    this)
                    .val();

                $
                    .ajax({
                    type: "GET",
                    url: '/ajax/transaction/bankBranch?id=' + bankId,
                    success: function(
                        response) {
                        var len = response.length;
                        var options = '';
                        for (var i = 0; i < len; ++i) {
                            options += '<option value="' + response[i].id + '">' + response[i].name + '</option>';
                        }
                        $(
                            '#branchId')
                            .html(
                            options);
                    }
                });

            });

        }
    });

    // commission value
    $('#payoutAgentId').change(function() {
        var payoutAgentId = $(this).val();

        $.ajax({
            type: "GET",
            url: '/ajax/transaction/commission?id=' + payoutAgentId,
            success: function(response) {
                var len = response.length;
                var options = '';
                for (var i = 0; i < len; ++i) {
                    options += '<option value="' + response[i].id + '">' + response[i].name + '</option>';
                }
                $('#sendingCommission').html(options);
            }
        });

    });

    // Exchange Rate value remitCountry
    $("#remitCountry")
        .change(function() {
        var remitCountry = $(this).val();
        $
            .ajax({
            type: "GET",
            url: '/ajax/transaction/exchangeRate?iso=' + remitCountry,
            success: function(response) {
                var len = response.length;
                var options = '';
                // console.log(response);
                for (var i = 0; i < len; ++i) {
                    console
                        .log(response[i].buyingRate);
                    options += '<option value="' + response[i].id + '">' + response[i].buyingRate + '</option>';
                }

                $('#exchangeRate')
                    .html(options);

            }

        });
    });

    // load super agent in user add
    $('#userTypeSelection').change(function() {

        var userType = $(this).val();
        if (userType == "Super_Agent") {
            getSuperAgent();
        } else if (userType == "Agent") {
            superAgentId = $(
                "#superAgentId>option:selected")
                .val();
            getSuperAgent(superAgentId);
            getAgent(superAgentId);
        }
        /*
         * else if(userType =="Customer"){ $.ajax({
         * type: "GET", url: "/ajax/user/customer",
         * success:function(response){ var len =
         * response.length; var options = ''; for(var
         * i=0; i<len; ++i) { options += '<option
         * value="'+response[i].id + '">'
         * +response[i].firstName+ '</option>'; }
         * $('#customerId').html(options); } }); }
         */
    });

    $('#superAgentId').change(function() {

        var superAgent = $(this).val();
        var userType = $('#userTypeSelection').val();
        if (userType == "Agent") {
            $.ajax({
                type: "GET",
                url: "/ajax/user/agent?id=" + superAgent,
                success: function(response) {
                    var len = response.length;
                    var options = '';
                    for (var i = 0; i < len; ++i) {
                        options += '<option value="' + response[i].id + '">' + response[i].firstName + '</option>';
                    }
                    $('#agentId').html(options);
                }
            });

        }
    });

    // selection change in USerType in User add
    $('.sntriesall').each(function() {
        $(this).hide();
    });
    $('.jqusersel')
        .on(
        'change', function() {
        if ($(this).find("option:selected")
            .val() == "Admin") {
            $('.sntriesall').each(function() {
                $(this).hide();
            });
        } else if ($(this).find(
            "option:selected").val() == "Super_Agent") {
            $('.sntriesall').each(function() {
                $(this).hide();
            });
            $('.first').show();
        } else if ($(this).find(
            "option:selected").val() == "Agent") {
            $('.sntriesall').each(function() {
                $(this).hide();
            });
            $('.first').show();
            $('.second').show();
        } else if ($(this).find(
            "option:selected").val() == "Customer") {
            $('.sntriesall').each(function() {
                $(this).hide();
            });
            $('.third').show();
        }
    });

    // transaction payout channel

    $('.tranPsel')
        .on(
        'change', function() {
        if ($(this).find("option:selected")
            .val() == "agent") {
            $('.payoutSup').each(function() {
                $(this).hide();
            });

        } else if ($(this).find(
            "option:selected").val() == "bankAccount") {
            $('.payoutSup').each(function() {
                $(this).hide();
            });

        }
    });
    // transaction auto calc

    $(".sndamt > input[type='text']")
        .on(
        'keyup change paste propertychange click ', function() {

        $(".totamt > input[type='text']")
            .val(
            parseFloat($(this)
            .val()) + parseFloat($(
            ".sendComm > input[type='text']")
            .val()));
    });

    $('.remcou').change(function() {

        $(".sendcur > input[type='text']").val(
            $(this).find('option:selected').attr(
            'currcode'));

        $(".exreet > input[type='text']").val(
            $(this).find('option:selected').attr(
            'exrate'));

    });
    $(".sendcur > input[type='text']").val(
        $(".remcou").find('option:selected').attr(
        'currcode'));

    $(".exreet > input[type='text']")
        .val(
        $(".remcou").find('option:selected').attr(
        'exrate'));

    $('.agent').change(function() {

        $(".sendComm > input[type='text']").val(
            $(this).find('option:selected').attr(
            'commission'));

    });
    $(".sendComm > input[type='text']").val(
        $(".agent").find('option:selected').attr(
        'commission'));

    $('.agent').change(function() {

        $(".disAmt > input[type='text']").val(
            $(this).find('option:selected').attr(
            'discount'));

    });
    $(".disAmt > input[type='text']").val(
        $(".agent").find('option:selected')
        .attr('discount'));

});

$(document).ready(function() {

    $('#padd').click(function() {
        $("#pickData option:selected").each(function() {

            $('#pickListResult').prepend(
                "<option id=" + $(this).attr('id') + ">" + $(this).text() + "</option>")
            $(this).remove();
        });
    });

    $('#paddAll').click(function() {
        $("#pickData option").each(function() {

            $('#pickListResult').append(
                "<option id=" + $(this).attr('id') + ">" + $(this).text() + "</option>")
            $(this).remove();
        });
    });

    $('#premove').click(function() {
        $("#pickListResult option:selected").each(function() {

            $('#pickData').prepend(
                "<option id=" + $(this).attr('id') + ">" + $(this).text() + "</option>")
            $(this).remove();
        });
    });

    $('#premoveAll').click(function() {
        $("#pickListResult option").each(function() {

            $('#pickData').append(
                "<option id=" + $(this).attr('id') + ">" + $(this).text() + "</option>")
            $(this).remove();
        });
    });

    $('.btnfloat ').click(function() {

        var data = [];
        $("#pickListResult option").each(function() {

            var ids = $(this).attr('id');

            data.push(ids);
        });
        $('#ids').val(data);

    });

});

// aml upload js

$(document).ready(function() {
    $('#chooseFile').bind('change', function() {
        var filename = $("#chooseFile").val();
        if (/^\s*$/.test(filename)) {
            $(".file-upload").removeClass('active');
            $("#noFile").text("No file chosen...");
        } else {
            $(".file-upload").addClass('active');
            $("#noFile").text(filename.replace("C:\\fakepath\\", ""));
        }
    });

});

// aml dropdown

$(document)
    .ready(function() {

    $("#amlUpload > tbody > tr:gt(0)").hide();

    $("#amlUpload > thead > tr > th > div > select").change(function() {

        var that = $(this);

        $("#amlUpload > tbody > tr").each(function() {
            if ($(this).index() == that.find(
                'option:selected').index())

            {
                $(this).fadeIn(500).siblings()
                    .hide();

            }
        })
    });

    $("#amlUpload > tbody > tr")
        .each(function() {

        if ($(this).find('.erms').text().length > 0) {

            $(this).fadeIn(500).siblings()
                .hide();
            that = $(this);
            $(
                "#amlUpload > thead > tr > th > div > select > option")
                .each(function() {
                if (that
                    .index() == $(
                    this)
                    .index()) {
                    $(this)
                        .attr(
                        'selected',
                        'selected');
                }
            });

        }
    })

});

$(document).ready(function() {

    $('.dropdown-toggle').click(function() {
        $(this).next('ul').toggle();
    });
});

$(document).ready(function() {
    $('.sah').hide();
    var is = 1;
    $('.gptype > select >option').each(function() {
        $(this).attr('sp', is);
        is++;
    })
    var iss = 1;
    $('.sah').each(function() {
        $(this).attr('spp', iss);
        iss++;
    });
    $('.gptype > select').change(function() {
        var svr = $(this).find('option:selected').attr('sp');

        $('.sah').each(function() {
            var sv = $(this).attr('spp');

            if (svr == sv) {
                $('.sah').hide();
                $(this).show();
            }
        });
    });

    var svr = $('.gptype > select').find('option:selected').attr('sp');

    $('.sah').each(function() {
        var sv = $(this).attr('spp');

        if (svr == sv) {
            $('.sah').hide();
            $(this).show();
        }
    });

});

$(function() {

    $('#sidebar-menu li  ul').slideUp();
    $('#sidebar-menu li').removeClass('active');
    $('#sidebar-menu li').on('click touchstart', function() {
        var link = $('a', this).attr('href');
        if (link) {
            window.location.href = link;
        } else {
            if ($(this).is('.active')) {
                $(this).removeClass('active');
                $('ul', this).slideUp();
            } else {
                $('#sidebar-menu li').removeClass('active');
                $('#sidebar-menu li ul').slideUp();
                $(this).addClass('active');
                $('ul', this).slideDown();
            }
        }
    });

    $('#menu_toggle').click(function() {
        if ($('body').hasClass('nav-md')) {
            $('body').removeClass('nav-md').addClass('nav-sm');
            $('.left_col').removeClass('scroll-view').removeAttr(
                'style');
            $('.sidebar-footer').hide();

            if ($('#sidebar-menu li').hasClass('active')) {
                $('#sidebar-menu li.active').addClass('active-sm')
                    .removeClass('active');
            }
        } else {
            $('body').removeClass('nav-sm').addClass('nav-md');
            $('.sidebar-footer').show();

            if ($('#sidebar-menu li').hasClass('active-sm')) {
                $('#sidebar-menu li.active-sm').addClass('active')
                    .removeClass('active-sm');
            }
        }
    });
});

/* Sidebar Menu active class */
$(function() {
    var url = window.location;

    $('#sidebar-menu a[href="' + url + '"]').parent('li').addClass(
        'current-page');
    $('#sidebar-menu a').filter(function() {
        return this.href == url;
    }).parent('li').addClass('current-page').parent('ul').slideDown().parent()
        .addClass('active');
});

/** ****** /left menu *********************** * */
/** ****** right_col height flexible *********************** * */
$(".right_col").css("min-height", $(window).height());
$(window).resize(function() {
    $(".right_col").css("min-height", $(window).height());
});
/** ****** /right_col height flexible *********************** * */

/** ****** tooltip *********************** * */
$(function() {
    $('[data-toggle="tooltip"]').tooltip()
})
/** ****** /tooltip *********************** * */
/** ****** progressbar *********************** * */
if ($(".progress .progress-bar")[0]) {
    $('.progress .progress-bar').progressbar(); // bootstrap 3
}
/** ****** /progressbar *********************** * */
/** ****** switchery *********************** * */
if ($(".js-switch")[0]) {
    var elems = Array.prototype.slice.call(document
        .querySelectorAll('.js-switch'));
    elems.forEach(function(html) {
        var switchery = new Switchery(html, {
            color: '#26B99A'
        });
    });
}
/** ****** /switcher *********************** * */
/** ****** collapse panel *********************** * */
// Close ibox function
$('.close-link').click(function() {
    var content = $(this).closest('div.x_panel');
    content.remove();
});

// Collapse ibox function
$('.collapse-link').click(function() {
    var x_panel = $(this).closest('div.x_panel');
    var button = $(this).find('i');
    var content = x_panel.find('div.x_content');
    content.slideToggle(200);
    (x_panel.hasClass('fixed_height_390') ? x_panel.toggleClass('')
        .toggleClass('fixed_height_390') : '');
    (x_panel.hasClass('fixed_height_320') ? x_panel.toggleClass('')
        .toggleClass('fixed_height_320') : '');
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    setTimeout(function() {
        x_panel.resize();
    }, 50);
});
/** ****** /collapse panel *********************** * */
/** ****** iswitch *********************** * */
if ($("input.flat")[0]) {
    $(document).ready(function() {
        $('input.flat').iCheck({
            checkboxClass: 'icheckbox_flat-green',
            radioClass: 'iradio_flat-green'
        });
    });
}
/** ****** /iswitch *********************** * */
/** ****** star rating *********************** * */
// Starrr plugin (https://github.com/dobtco/starrr)
var __slice = [].slice;

(function($, window) {
    var Starrr;

    Starrr = (function() {
        Starrr.prototype.defaults = {
            rating: void 0,
            numStars: 5,
            change: function(e, value) {}
        };

        function Starrr($el, options) {
            var i, _, _ref, _this = this;

            this.options = $.extend({}, this.defaults, options);
            this.$el = $el;
            _ref = this.defaults;
            for (i in _ref) {
                _ = _ref[i];
                if (this.$el.data(i) != null) {
                    this.options[i] = this.$el.data(i);
                }
            }
            this.createStars();
            this.syncRating();
            this.$el.on('mouseover.starrr', 'span', function(e) {
                return _this.syncRating(_this.$el.find('span').index(
                    e.currentTarget) + 1);
            });
            this.$el.on('mouseout.starrr', function() {
                return _this.syncRating();
            });
            this.$el.on('click.starrr', 'span', function(e) {
                return _this.setRating(_this.$el.find('span').index(
                    e.currentTarget) + 1);
            });
            this.$el.on('starrr:change', this.options.change);
        }

        Starrr.prototype.createStars = function() {
            var _i, _ref, _results;

            _results = [];
            for (_i = 1, _ref = this.options.numStars; 1 <= _ref ? _i <= _ref : _i >= _ref; 1 <= _ref ? _i++ : _i--) {
                _results
                    .push(this.$el
                    .append("<span class='glyphicon .glyphicon-star-empty'></span>"));
            }
            return _results;
        };

        Starrr.prototype.setRating = function(rating) {
            if (this.options.rating === rating) {
                rating = void 0;
            }
            this.options.rating = rating;
            this.syncRating();
            return this.$el.trigger('starrr:change', rating);
        };

        Starrr.prototype.syncRating = function(rating) {
            var i, _i, _j, _ref;

            rating || (rating = this.options.rating);
            if (rating) {
                for (i = _i = 0, _ref = rating - 1; 0 <= _ref ? _i <= _ref : _i >= _ref; i = 0 <= _ref ? ++_i : --_i) {
                    this.$el.find('span').eq(i).removeClass(
                        'glyphicon-star-empty').addClass('glyphicon-star');
                }
            }
            if (rating && rating < 5) {
                for (i = _j = rating; rating <= 4 ? _j <= 4 : _j >= 4; i = rating <= 4 ? ++_j : --_j) {
                    this.$el.find('span').eq(i).removeClass('glyphicon-star')
                        .addClass('glyphicon-star-empty');
                }
            }
            if (!rating) {
                return this.$el.find('span').removeClass('glyphicon-star')
                    .addClass('glyphicon-star-empty');
            }
        };

        return Starrr;

    })();
    return $.fn.extend({
        starrr: function() {
            var args, option;

            option = arguments[0], args = 2 <= arguments.length ? __slice.call(
                arguments, 1) : [];
            return this.each(function() {
                var data;

                data = $(this).data('star-rating');
                if (!data) {
                    $(this).data('star-rating', (data = new Starrr($(this), option)));
                }
                if (typeof option === 'string') {
                    return data[option].apply(data, args);
                }
            });
        }
    });
})(window.jQuery, window);

$(function() {
    return $(".starrr").starrr();
});

$(document).ready(function() {

    $('#stars').on('starrr:change', function(e, value) {
        $('#count').html(value);
    });

    $('#stars-existing').on('starrr:change', function(e, value) {
        $('#count-existing').html(value);
    });

});
/** ****** /star rating *********************** * */
/** ****** table *********************** * */
$('table input').on('ifChecked', function() {
    check_state = '';
    $(this).parent().parent().parent().addClass('selected');
    countChecked();
});
$('table input').on('ifUnchecked', function() {
    check_state = '';
    $(this).parent().parent().parent().removeClass('selected');
    countChecked();
});

var check_state = '';
$('.bulk_action input').on('ifChecked', function() {
    check_state = '';
    $(this).parent().parent().parent().addClass('selected');
    countChecked();
});
$('.bulk_action input').on('ifUnchecked', function() {
    check_state = '';
    $(this).parent().parent().parent().removeClass('selected');
    countChecked();
});
$('.bulk_action input#check-all').on('ifChecked', function() {
    check_state = 'check_all';
    countChecked();
});
$('.bulk_action input#check-all').on('ifUnchecked', function() {
    check_state = 'uncheck_all';
    countChecked();
});

function countChecked() {
    if (check_state == 'check_all') {
        $(".bulk_action input[name='table_records']").iCheck('check');
    }
    if (check_state == 'uncheck_all') {
        $(".bulk_action input[name='table_records']").iCheck('uncheck');
    }
    var n = $(".bulk_action input[name='table_records']:checked").length;
    if (n > 0) {
        $('.column-title').hide();
        $('.bulk-actions').show();
        $('.action-cnt').html(n + ' Records Selected');
    } else {
        $('.column-title').show();
        $('.bulk-actions').hide();
    }
}
/** ****** /table *********************** * */
/** ****** *********************** * */
/** ****** *********************** * */
/** ****** *********************** * */
/** ****** *********************** * */
/** ****** *********************** * */
/** ****** *********************** * */
/** ****** Accordion *********************** * */

$(function() {
    $(".expand").on("click", function() {
        $(this).next().slideToggle(200);
        $expand = $(this).find(">:first-child");

        if ($expand.text() == "+") {
            $expand.text("-");
        } else {
            $expand.text("+");
        }
    });
});

/** ****** Accordion *********************** * */

/** ****** scrollview *********************** * */
/*
 * $(document).ready(function() {
 * 
 * $(".scroll-view").niceScroll({ touchbehavior : true, cursorcolor : "rgba(42,
 * 63, 84, 0.35)" });
 * 
 * });
 */
/** ****** /scrollview *********************** * */

/** ****** NProgress *********************** * */
if (typeof NProgress != 'undefined') {
    $(document).ready(function() {
        NProgress.start();
    });

    $(window).load(function() {
        NProgress.done();
    });
}

function getSuperAgent() {
    $.ajax({
        type: "GET",
        url: "/ajax/user/superagent",
        success: function(data) {
            // console.log(response);
            // var len = response.length;
            $('#superAgentId').find('option').remove();

            $('#customerId').find('option').remove();
            var option = '';
            $.each(data.superAgentList, function(index, superAgent) {
                option = '<option value="' + superAgent.id + '">' + superAgent.agencyName + '</option>';
                $('#superAgentId').append(option);
            });
            /*
             * for(obj in response) { console.log(obj.firstName); option = '<option
             * value="'+obj.id + '">' +obj.firstName+ '</option>';
             * $('#superAgentId').append(option); }
             */
            $('#superagent-div').show();
        }
    });

}

function getAgent(id) {
    $.ajax({
        type: "GET",
        url: "/ajax/user/agent",
        data: {
            superAgentId: id
        },
        success: function(data) {
            // var len = response.length;
            var options = '';

            $('#agentId').find('option').remove();

            $.each(data.agentList, function(index, agent) {
                option = '<option value="' + agent.id + '">' + agent.agencyName + '</option>';
                $('#agentId').append(option);
            });
            /*
             * for(var i=0; i<len; ++i) { options = '<option
             * value="'+response[i].id + '">' +response[i].firstName+ '</option>';
             * $("#agentId").append(option); }
             */

            $('#agent-div').show();
        }
    });
}

function showReceiverDetails(countryIso) {

}

function getCustomer() {}
/** ****** NProgress *********************** * */