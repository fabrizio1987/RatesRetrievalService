/**
 * Created by Piero on 17/05/16.
 */
$(document).ready(function () {
    // Hide the button to show the rate list before starting the job
    //$('#btnRateList').hide();
    //$('#message').hide();
    var selectedLink;

    /************************************************************/
    /* Dialog creation */
    /************************************************************/
    $('#dialog-confirm-delete-single').dialog({
        resizable: false,
        height: 200,
        modal: true,
        autoOpen: false,
        buttons: {
            "Delete Rate": function () {
                $(this).dialog("close");
                window.location.href = selectedLink.attr('href');
            },
            "Cancel": function () {
                $(this).dialog("close");
            }
        }
    });

    $('#dialog-confirm-delete-all').dialog({
        resizable: false,
        height: 200,
        modal: true,
        autoOpen: false,
        buttons: {
            "Delete Rate": function () {
                $(this).dialog("close");
                window.location.href = selectedLink.attr('href');
            },
            "Cancel": function () {
                $(this).dialog("close");
            }
        }
    });

    /************************************************************/
    /* End Dialog creation */
    /************************************************************/

    var divMessage = $('#message');
    $('#btnStartJob').on('click', function () {
        $.ajax({
            url: "api/job",
            method: "POST",
            contentType: "text/plain; charset=UTF-8"
        }).done(function (data) {
            divMessage.append(data);
            $('#btnRateList').show();
            divMessage.show();
        })
    });



    $('.actionDelete').on('click', function (event) {
        event.preventDefault();
        selectedLink = $(this);

        $('#dialog-confirm-delete-single').dialog('open');
    });
    $('#actionDeleteAll').on('click', function (event) {
        event.preventDefault();
        selectedLink = $(this);

        $('#dialog-confirm-delete-all').dialog('open');
    });
});
