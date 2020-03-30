// Control logic for the processor

package dinocpu.components

import chisel3._
import chisel3.util.{BitPat, ListLookup}

/**
 * Main control logic for our simple processor
 *
 * Input: opcode:     Opcode from instruction
 *
 * Output: branch     true if branch or jump and link (jal). update PC with immediate
 * Output: pcfromalu  Use the pc from the ALU, not pc+4 or pc+imm
 * Output: jump       True if we want to update the PC with pc+imm regardless of the ALU result
 * Output: memread    true if we should read from memory
 * Output: memwrite   true if writing to the data memory
 * Output: regwrite   true if writing to the register file
 * Output: toreg      00 for result from alu, 01 for immediate, 10 for pc+4, 11 for data from memory
 * Output: alusrc     source for the second ALU input (0 is readdata2 and 1 is immediate)
 * Output: pcadd      Use PC as the input to the ALU
 * Output: itype      True if we're working on an itype instruction
 * Output: aluop      00 for ld/st, 10 for R-type, 01 for branch
 * Output: validinst  True if the instruction we're decoding is valid
 *
 * For more information, see section 4.4 of Patterson and Hennessy.
 * This follows figure 4.22.
 */

class Control extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))

    val branch    = Output(Bool())
    val pcfromalu = Output(Bool())
    val jump      = Output(Bool())
    val memread   = Output(Bool())
    val memwrite  = Output(Bool())
    val regwrite  = Output(Bool())
    val toreg     = Output(UInt(2.W))
    val alusrc    = Output(Bool())
    val pcadd     = Output(Bool())
    val itype     = Output(Bool())
    val aluop     = Output(UInt(2.W))
    val validinst = Output(Bool())
  })

  val signals =
    ListLookup(io.opcode,
      /*default*/           List(false.B, false.B,   false.B, false.B,   false.B,  false.B,  0.U,   false.B, false.B, false.B, 0.U,   false.B),
      Array(              /*     branch,  pcfromalu, jump,    memread,   memwrite, regwrite, toreg, alusrc,  pcadd,   itype,   aluop, validinst */
      // R-format
      BitPat("b0110011") -> List(false.B, false.B,   false.B, false.B,   false.B,  true.B,   0.U,   false.B, false.B, false.B, 2.U,   true.B),

      // Your code coes here for Lab 2.
      // Remember to make sure to have commas at the end of each line

      ) // Array
    ) // ListLookup

  io.branch    := signals(0)
  io.pcfromalu := signals(1)
  io.jump      := signals(2)
  io.memread   := signals(3)
  io.memwrite  := signals(4)
  io.regwrite  := signals(5)
  io.toreg     := signals(6)
  io.alusrc    := signals(7)
  io.pcadd     := signals(8)
  io.itype     := signals(9)
  io.aluop     := signals(10)
  io.validinst := signals(11)
}