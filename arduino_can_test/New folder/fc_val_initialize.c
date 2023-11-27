/*
 * Academic License - for use in teaching, academic research, and meeting
 * course requirements at degree granting institutions only.  Not for
 * government, commercial, or other organizational use.
 *
 * fc_val_initialize.c
 *
 * Code generation for function 'fc_val_initialize'
 *
 */

/* Include files */
#include "fc_val_initialize.h"
#include "fc_val_data.h"
#include "rt_nonfinite.h"

/* Function Definitions */
void fc_val_initialize(void)
{
  rt_InitInfAndNaN();
  isInitialized_fc_val = true;
}

/* End of code generation (fc_val_initialize.c) */
