import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SwitchCredenciaisComponent } from './credenciais-usuario.component';


describe('SwitchCredenciaisComponent', () => {
  let component: SwitchCredenciaisComponent;
  let fixture: ComponentFixture<SwitchCredenciaisComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SwitchCredenciaisComponent]
    });
    fixture = TestBed.createComponent(SwitchCredenciaisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
