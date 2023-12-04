import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmDestaqueComponent } from './em-destaque.component';

describe('EmDestaqueComponent', () => {
  let component: EmDestaqueComponent;
  let fixture: ComponentFixture<EmDestaqueComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmDestaqueComponent]
    });
    fixture = TestBed.createComponent(EmDestaqueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
