package calc.nodes.visitors.codeGen;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

import calc.nodes.visitors.tychkvisitor.*;


public  class CodeGen {

    private LinkedList<String> lines;
    private int lbCounter;

    public CodeGen(){
        lines= new LinkedList<String>();
        lbCounter = 0;
    }

    public String newLabel(String infix){
          return "_lb_" + infix + "_"+lbCounter++;
    }

    public void emit(String l){
        lines.add(l);
    }

    public int getCodeSize(){return lines.size();}

    public void labelInstrAt(String lb, int p){
        lines.add(p,lb + ":");
    }

    public void printCode(){
        printHeader();
        for(String l : lines){
            System.out.println("    " + l);
        }
        printFuncs();
    }

    private void printHeader(){
        System.out.println("section .data");
        System.out.println("msg1 : db 0xA,'resultado: '");
        System.out.println("strLen : equ $-msg1");
        System.out.println("msg2 : db '------ end-of-program ----- ',0xA");
        System.out.println("strLen2 : equ $-msg2");
        System.out.println("");
        System.out.println("segment .bss");
        System.out.println("_outbuff: resb 256");
        System.out.println("_outsz: resq 1");
        System.out.println("_outstart: resq 1");
        System.out.println("");
        System.out.println("_inbuff: resb 256");
        System.out.println("_insz:  resq 1");
        System.out.println("_instart:  resq 1");
        System.out.println("");
        System.out.println("section .text");
        System.out.println("global _start");
        System.out.println("");
        System.out.println("_start:");
        System.out.println("    push rbp");
        System.out.println("    mov rbp, rsp");
        System.out.println("    ");
    }


    private void printFuncs(){

        System.out.println("; Converte um inteiro em String e o imprime para a saída padrão. ");
        System.out.println("; eax contém o valor a ser impresso. ");
        System.out.println("; usa esi, ebx, ecx, edx, ");
        System.out.println("; sobrescreve eax, ");
        System.out.println("; Não salva registradores. ");
        System.out.println("_printInt: ");
        System.out.println("push rbp     ; Salvando a base da pilha; ");
        System.out.println("    mov rbp, rsp ; atualizamos o topo; ");
        System.out.println("  ");
        System.out.println("    mov rsi, _outbuff   ; rsi aponta para o buffer de saida ");
        System.out.println("    add rsi, 255        ;Vamos ao final do buffer ! ");
        System.out.println("    mov [rsi], BYTE 10  ;No final do buffer inserimos uma quebra de linha. ");
        System.out.println("    dec rsi             ; rsi -- ");
        System.out.println("  ");
        System.out.println("    call _intToStr ");
        System.out.println("  ");
        System.out.println("    mov rbx, _outbuff ");
        System.out.println("    add rbx, 255 ");
        System.out.println("    sub rbx, rax ");
        System.out.println("    mov [_outstart], rbx ");
        System.out.println("    add rax,1 ");
        System.out.println("    mov [_outsz], rax ");
        System.out.println("  ");
        System.out.println("    mov rax, 1        ; 1 Código para operação de escrita ");
        System.out.println("    mov rdi, 1        ; Hanlder de escrita (1 = stdout) ");
        System.out.println("    mov rsi, msg1     ; Endereço da mensagem ");
        System.out.println("    mov rdx, strLen  ; Comprimento da saída ");
        System.out.println("    syscall           ; Chamada ao kernel (64 bits, int 0x80  para 32 bits ) ");
        System.out.println("  ");
        System.out.println("    mov rax, 1           ; 1 Código para operação de escrita ");
        System.out.println("    mov rdi, 1           ; Hanlder de escrita (1 = stdout) ");
        System.out.println("    mov rsi, [_outstart] ; Endereço da mensagem ");
        System.out.println("    mov rdx, [_outsz]    ; Comprimento da saída ");
        System.out.println("    syscall              ; Chamada ao kernel (64 bits, int 0x80  para 32 bits ) ");
        System.out.println("  ");
        System.out.println("    pop rbp      ; ");
        System.out.println("    ret           ; retornado da função; ");
        System.out.println("  ");
        System.out.println("   ");
        System.out.println("   ; Converte um inteiro em string");
        System.out.println("  ; rax valor p/ converter");
        System.out.println("  ; rsi ponteiro o fim do buffer onde escrever a string");
        System.out.println("  ; deixa em rax, o número de bytes escritos.");
        System.out.println("  ; usa rcx, rbx não salva registradores.");
        System.out.println("_intToStr:");
        System.out.println("    mov rbx, rsi              ; copia o end do fim do buffer");
        System.out.println("    mov rcx, 10               ; base da conversao");
        System.out.println("_tostr:");
        System.out.println("    mov rdx,0                 ; edx  (a parte mais alta )");
        System.out.println("    idiv rcx                  ; divide edx:eax por ecx");
        System.out.println("    add dl, 48                ; 48 é código ascii para '0'");
        System.out.println("    mov [rsi], dl;            ; colocamos o carácter obtido em dl no valor apontado por ebx");
        System.out.println("    dec rsi                   ; esi --");
        System.out.println("    cmp rax,0                 ;");
        System.out.println("    jnz _tostr              ; Se eax > 0; volte e conte outra vez !!!");
        System.out.println("    inc rsi           ; Calcula o início do número e seu comprimento na string de saída.");
        System.out.println("    sub rbx, rsi");
        System.out.println("    mov rax, rbx");
        System.out.println("    add rax, 1");
        System.out.println("    ret");
    }


}
