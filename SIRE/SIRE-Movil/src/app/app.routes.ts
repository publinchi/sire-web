import {Routes, RouterModule} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {HomePage} from '../pages/home/home';
import {PedidoPage} from '../pages/pedido/pedido';

export const routes: Routes = [
    {path: '', component: HomePage},
    {path: 'pedido', component: PedidoPage}
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
