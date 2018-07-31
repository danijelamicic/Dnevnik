import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditpredajeComponent } from './editpredaje.component';

describe('EditpredajeComponent', () => {
  let component: EditpredajeComponent;
  let fixture: ComponentFixture<EditpredajeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditpredajeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditpredajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
