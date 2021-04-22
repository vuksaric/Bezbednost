import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { CreateCertificateComponent } from './pages/create-certificate/create-certificate.component';
import { ListCertificatesComponent } from './pages/list-certificates/list-certificates.component';
import { UserHomePageComponent } from './pages/user-home-page/user-home-page.component';
import { AdminHomePageComponent } from './pages/admin-home-page/admin-home-page.component';
import { FrontPageComponent } from './pages/front-page/front-page.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'front-page'},
  {path: 'frontpage', component:FrontPageComponent, children: [
    {path: 'admin-home-page', component: AdminHomePageComponent},
    {path: 'user-home-page/:id', component: UserHomePageComponent},
    {path: 'login', component: LoginComponent},
    {path: 'login/:id', component: LoginComponent },
    {path: 'register', component: RegistrationComponent},
    {path: 'list-certificates', component: ListCertificatesComponent },
    {path: 'create-certificate', component: CreateCertificateComponent}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
