import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PogleducenikComponent } from './pogleducenik.component';

describe('PogleducenikComponent', () => {
  let component: PogleducenikComponent;
  let fixture: ComponentFixture<PogleducenikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PogleducenikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PogleducenikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
