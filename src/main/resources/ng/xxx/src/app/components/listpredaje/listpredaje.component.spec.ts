import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListpredajeComponent } from './listpredaje.component';

describe('ListpredajeComponent', () => {
  let component: ListpredajeComponent;
  let fixture: ComponentFixture<ListpredajeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListpredajeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListpredajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
