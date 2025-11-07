/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

  setTimeout(() => {
        document.querySelectorAll('.toast').forEach(el => el.remove());
    }, 5000); // removes after 5 seconds


  function redirectAfterDelay() {
        setTimeout(function() {
            window.location.href = 'dashboard.xhtml';
        }, 2000); // 2 seconds delay
    }