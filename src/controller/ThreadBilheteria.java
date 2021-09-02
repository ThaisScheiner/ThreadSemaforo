package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadBilheteria extends Thread 
{

	private int tempoLogin;
	private int tempoCompra;
	private String cliente;
	private int numeroIngresso;
	private static int ingressos = 100;
	private Semaphore controle;

	Random numAleatorio = new Random();

	public ThreadBilheteria(String cliente, int numeroIngresso, Semaphore controle) 
	{
		super();
		this.cliente = cliente;
		this.numeroIngresso = numeroIngresso;
		this.controle = controle;

	}

	@Override
	public void run() 
	{

		if (logar()) 
		{
			if (realizaCompra()) 
			{

				comprar();
			}

		}
		super.run();
	}

	private void comprar() 
	{

		try 
		{
			controle.acquire();

			if (numeroIngresso <= ingressos) 
			{

				controleIngressos();

			} 
			else 
			{
				System.err.println("Cliente " + cliente + " tentou comprar " + numeroIngresso + " mas somente "
						+ ingressos + " Disponiveis");
			}

		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			controle.release();
		}

	}

	private boolean logar() 
	{
		tempoLogin = numAleatorio.nextInt(2001) + 50;
		try 
		{
			sleep(tempoLogin);
			if (tempoLogin < 1000)
			{

				System.out.println("Cliente " + cliente + " logado... \n");

				return true;

			} 
			else 
			{
				System.err.println("Cliente " + cliente + " expirou tempo login");
			}

		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	private boolean realizaCompra() 
	{
		tempoCompra = numAleatorio.nextInt(3001) + 1000;

		try 
		{

			sleep(tempoCompra);

			if (tempoCompra < 2500) 
			{
				return true;

			} 
			else 
			{
				System.err.println("Cliente " + cliente + " expirou tempo COMPRA");
			}

		}

		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}

		return false;
	}

	private void controleIngressos() 
	{

		ingressos -= numeroIngresso;
		System.out.println("O Cliente " + cliente + " comprou " + numeroIngresso + " ingressos, restando " + ingressos
				+ " ingressos\n");

	}

}