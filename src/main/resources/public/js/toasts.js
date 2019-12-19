deferToast(function () {
    $(function(){
        let toastContainer = '<div id="toastContainer" style="position: fixed; top: 60px; right: 5px; left: auto; bottom: auto; z-index: 999999"></div>';
        $('body').append(toastContainer);
    });
});

function deferToast(method){
    if (window.jQuery) {
        method();
    } else {
        setTimeout(function() { deferToast(method) }, 100);
    }
}

var toastCant = 1;

/**
 * Mini librería creada alrededor de los Toasts de Bootstrap para simplificar su uso
 *
 * @param mensaje
 * @param tipo
 * @param time
 * @returns {{update: update, id: string}}
 */
function toast(mensaje, tipo, time = 5000) {
    let color;
    let bgColor;
    let icon;
    let leyenda;
    let toastId = 'toast'+toastCant;
    toastCant++;

    if (tipo === 'info'){
        bgColor = '#d1ecf1';
        color = '#0c5460';
        icon = '<i class="fal fa-info"></i>';
        leyenda = 'Info';

    } else if (tipo === 'success'){
        bgColor = '#d4edda';
        color = '#155724';
        icon = '<i class="fal fa-check"></i>';
        leyenda = 'Exito';

    } else if (tipo === 'error'){
        bgColor = '#f8d7da';
        color = '#721c24';
        icon = '<i class="fal fa-exclamation-triangle"></i>';
        leyenda = 'Error';
    }
    let notificacion = '<div class="toast shadow" id="'+toastId+'" role="alert" aria-live="assertive" aria-atomic="true" data-delay="'+time+'" style="position: relative; display: block; min-width: 250px; max-width: 251px;">' +
        '<div class="toast-header py-0" style="background: '+bgColor+'; color: '+color+'">' + icon +
        '<strong class="mr-auto">&nbsp'+ leyenda +'</strong>'+
        '<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close" style="padding-right: 0!important">' +
        '<span aria-hidden="true" style="color: #000;">&times;</span>' +
        '</button>' +
        '</div>' +
        '<div class="toast-body" style="-ms-word-wrap: break-word;word-wrap: break-word; color: #000;">' + mensaje +
        '</div>' +
        '</div>';

    $('#toastContainer').prepend(notificacion);

    let toastSelector = $('#'+toastId);

    toastSelector.toast('show');

    toastSelector.on('hidden.bs.toast', function(){
        $(this).remove();
    });

    return {
        id: toastId,
        update: function (mensaje, tipo, time = 5000) {
            let notificacion = $('#' + toastId);
            setTimeout(function () {
                notificacion.toast('hide');
                return toast(mensaje, tipo, time);
            }, 2000);
        }
    };
}