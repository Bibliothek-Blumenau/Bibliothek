import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarSistemaComponent } from './navbar-sistema.component';

describe('NavbarSistemaComponent', () => {
  let component: NavbarSistemaComponent;
  let fixture: ComponentFixture<NavbarSistemaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarSistemaComponent]
    });
    fixture = TestBed.createComponent(NavbarSistemaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
