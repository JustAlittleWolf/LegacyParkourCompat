# Immutable supplement 1: resolver trace correction

This supplement corrects the resolver analysis in `reviewer-verdict.md` without modifying that published audit.

## Exact resolver trace

`ChangeResolver.resolve` retains changes for which `change.emulates().newerThanOrEqual(selected)` is true, then chooses the eligible change with the smallest enum ordinal.

- For selection `V1_13`, candidate `V1_12` is earlier than the selection and is ineligible.
- Candidate `V1_13_1` is later than or equal to `V1_13`, so it is eligible and is the winning candidate among the two providers.
- For selections `V1_13_1` and later, the grouped `V1_13_1` provider is likewise eligible and wins over `V1_12`.

Therefore, **the candidate's `V1_13_1` grouped fence correctly covers 1.13.0 as well as 1.13.1/1.13.2 under this resolver**. The earlier audit's finding that 1.13 selection resolves to the old expression, and its proposed move to `V1_13`, are withdrawn. No version-fence defect remains on this point.

## Scope of the remaining findings

- The pre-1.9 native-fallback requirement remains an integrator requirement: if the operation hook is invoked for a selected 1.8 profile, `V1_12` is eligible under the resolver. Elytra/fall-flying did not exist in 1.8, so the caller must preserve the modern native operation before the 1.9 feature boundary.
- The Slow Falling native-fallback requirement remains: its modern-only gravity branch must not be rewritten as historical movement. The candidate interface/body fragment does not itself establish how the caller will preserve native behavior in that branch.
- The candidate commit is a provider fragment, not a full integration. Its lack of dispatch and native fallback is an integration dependency, not a defect in the submitted arithmetic bodies or source finding. Runtime parity is consequently not established by this review.

The isolated arithmetic acceptance and all source/hash identifiers in the original audit remain unchanged.
