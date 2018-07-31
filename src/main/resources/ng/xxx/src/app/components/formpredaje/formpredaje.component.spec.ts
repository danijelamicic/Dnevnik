import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormpredajeComponent } from './formpredaje.component';

describe('FormpredajeComponent', () => {
  let component: FormpredajeComponent;
  let fixture: ComponentFixture<FormpredajeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormpredajeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormpredajeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
