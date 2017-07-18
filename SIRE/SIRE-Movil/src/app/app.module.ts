import {ScheduleModule} from 'primeng/components/schedule/schedule';
import {InputTextModule} from 'primeng/primeng';
import {AccordionModule} from 'primeng/primeng';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';;
import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {IonicApp, IonicErrorHandler, IonicModule} from 'ionic-angular';

import {AppComponent} from './app.component';
import {HomePage} from '../pages/home/home';
import {ListPage} from '../pages/list/list';
import {PedidoPage} from '../pages/pedido/pedido';
import {AppTopBar} from './app.topbar.component';
import {InlineProfileComponent} from './app.profile.component';
import {AppMenuComponent, AppSubMenu} from './app.menu.component';

import {StatusBar} from '@ionic-native/status-bar';
import {SplashScreen} from '@ionic-native/splash-screen';

@NgModule({
    declarations: [
        AppComponent,
        HomePage,
        ListPage,
        PedidoPage,
        AppTopBar,
        InlineProfileComponent,
        AppMenuComponent,
        AppSubMenu
    ],
    imports: [
        BrowserModule,
        IonicModule.forRoot(AppComponent),
        ScheduleModule,
        InputTextModule,
        AccordionModule,
        BrowserAnimationsModule
    ],
    bootstrap: [IonicApp],
    entryComponents: [
        AppComponent,
        HomePage,
        ListPage,
        PedidoPage
    ],
    providers: [
        StatusBar,
        SplashScreen,
        {provide: ErrorHandler, useClass: IonicErrorHandler}
    ]
})
export class AppModule {}
