import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddpredajeComponent } from './addpredaje.component';

describe('AddpredajeComponent', () => {
  let component: AddpredajeComponent;
  let fixture: ComponentFixture<AddpredajeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddpredajeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddpredajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
