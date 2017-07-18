import {Component, OnInit, OnDestroy} from '@angular/core';
import {IonicPage, NavController, NavParams} from 'ionic-angular';

/**
 * Generated class for the PedidoPage page.
 *
 * See http://ionicframework.com/docs/components/#navigation for more info
 * on Ionic pages and navigation.
 */
@IonicPage()
@Component({
    selector: 'page-pedido',
    templateUrl: 'pedido.html',
})
export class PedidoPage implements OnInit, OnDestroy {
    date: Date;
    timerId: number;
    constructor(public navCtrl: NavController, public navParams: NavParams) {
        this.date = new Date();
    }

    ionViewDidLoad() {
        console.log('ionViewDidLoad PedidoPage');
    }

    updateDate() {
        this.date = new Date();
    }

    ngOnDestroy(): void {
        clearInterval(this.timerId);
    }

    ngOnInit(): void {
        this.timerId = (setInterval(() => this.updateDate()), 1000);
    }

}
