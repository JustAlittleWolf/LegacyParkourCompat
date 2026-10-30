# Independent provider closure: Lever, LilyPad, and Liquid

This supplemental pass closes the three paired direct-provider rows omitted from `provider-formulas/b.md`'s described M–S range. It compares only the exact assigned source trees and uses the provider inventory as a locator.

Manifest SHA-256 values: 1.7.10 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`; 1.8 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`.

| Class | 1.7.10 SHA-256 | 1.8 SHA-256 | Compared collision behavior |
|---|---|---|---|
| `LeverBlock.java` | `B0472A1BB1A164D593190BADCEDFF67E1C6C4E12E3A928772FBFA1D064EB8868` | `17C0FD2FF3D95B23892C0455FFA6071FEA147EA05E8C0E8D21B2B56B56D8C734` | Direct provider returns null on both sides. Orientation-based `updateShape` bounds differ only through their corresponding metadata/state cases and do not feed the direct null collision provider. No block collision is appended for a lever. |
| `LilyPadBlock.java` | `564F45EFDAF316FB495C4805974A44727A996A0EAAE625E440DE3155FC2FCBD1` | `9A5544144F65FDD51B072240549F43D53E9CD81DBAC5DAFB5C8E689F575548AD` | Both set the same thin full-XZ block shape (Y height `1/64`), return a fresh box from those bounds, and skip base collision collection only when the queried entity is a `BoatEntity`. For an ordinary player query, same box and dispatch; the provider returned box is fresh, also closing MovingBlock alias concern for this class. |
| `LiquidBlock.java` | `388A62989C7B8FBEF58CDA0F7EE517080E24BB59B2ABE01EB2835A807AEFEFF6` | `E4EF9385D025AA4672D4B6A55AFC72803267D0721469BF036217AB30A7760FE3` | Direct provider returns null on both sides. Liquid travel/material sampling is a separate callback/effect lane and has been paired there; this provider contributes no solid collision box. |

All three direct collision operations are equivalent for ordinary local-player block queries. No additional historical movement candidate was found. This report closes only the named omitted rows and does not assert full-pair runtime parity.
