# Immutable verdict rebind 001

## Rebind result: ACCEPT for the reviewed source contract plus correction

The original `review.md` remains unchanged with its historical **REVISE** verdict. The focused correction closes that bounded evidence gap; the combined report and correction now support the 1.13.1/.2 block-stream-before-entity-stream claim. No other part of the review was reopened.

## Exact-hash delta checked

- Correction SHA-256 matches its assigned value: `945d71dacef1b4df18580a57cc7f1784f7ffe80ac76b72dfb76624ede2ecc3d5`.
- `WorldAccess.java` hashes in the canonical 1.13.1 and 1.13.2 roots both match the supplement's value: `fc75177d629ef8d0d491821fe557e21606e4cb381164f1ca41b2763073f854be`.
- In each `WorldAccess.java`, line 18 extends `WorldView`; the full 66-line interface has no `getCollisions` declaration or override.
- The hash-bound `WorldView.java` files are identical in both releases. Lines 170-178 perform the border-state handling and return `getBlockCollisions(shape, excludeBlock, bl2)`. Lines 112-152 define that result as the partial-shape stream concatenated with its filtered full-cell accumulator tail.
- The already hash-bound concrete `World.java` override in both releases obtains `WorldAccess.super.getCollisions(...)` and concatenates entity collision shapes afterward. The combined inheritance and method bodies establish block stream first, entity stream second for this override.

Thus the earlier gap is closed without broadening the claim beyond the concrete `World.getCollisions` override. The original numerical slab witness was not rerun. No other sources, product code, or lane artifacts were inspected for this rebind; no build, test, or runtime was run.