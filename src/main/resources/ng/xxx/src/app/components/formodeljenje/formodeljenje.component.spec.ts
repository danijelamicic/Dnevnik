import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormodeljenjeComponent } from './formodeljenje.component';

describe('FormodeljenjeComponent', () => {
  let component: FormodeljenjeComponent;
  let fixture: ComponentFixture<FormodeljenjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormodeljenjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormodeljenjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
