package com.example.blockchainapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import java.math.BigInteger;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.functions.Consumer;
/*
package com.example.blockchainapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

      */
/*  Web3j web3 = Web3j.build(new HttpService("https://eth-mainnet.g.alchemy.com/v2/CqXgs0nSXfi_GUHd7qxDKU6RpEAzQnqq"));

      //  Web3j web3 = Web3j.build(new HttpService("https://infura.io/v3/YOUR_INFURA_PROJECT_ID"));

       // Example contract address and ABI
        String contractAddress = "0xYourSmartContractAddress";
        Credentials credentials = Credentials.create("d8302738eee5c5bb9908e938e4750a6c6d2638f53624ffd9c7143f0f8fbba39a");  // If you are not using MetaMask for signing

        MySmartContract contract = MySmartContract.load(contractAddress, web3, credentials, new DefaultGasProvider());

        // Calling a function from the contract
        String data = contract.someFunction().send();
        System.out.println("Contract Data: " + data);*//*




        Web3j web3 = Web3j.build(new HttpService("https://sepolia.infura.io/v3/7671a5a1557f4fdfad42fe0e6cd9fc05"));
        Credentials credentials = Credentials.create("d8302738eee5c5bb9908e938e4750a6c6d2638f53624ffd9c7143f0f8fbba39a");
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        A a = A.load(CONTRACT_ADDRESS, web3, credentials, contractGasProvider);
        a.retrieve().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer[BigInteger]() {
            @Override
            public void accept(BigInteger bigInteger) throws Exception {
                Log.i("vac", "accept: " + bigInteger);
            }
        });
        a.store(new BigInteger("123")).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer[TransactionReceipt]() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                Log.i("vac", "accept: ");
            }
        });

    }
}*/


public class MainActivity extends AppCompatActivity {

    private A contract;
    private Web3j web3;
    private Credentials credentials;
    private TextView tvBalance;

    private static final String CONTRACT_ADDRESS = ""; // Replace with your contract address
    private static final String PRIVATE_KEY = ""; // Replace with your private key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        Button btnDeposit = findViewById(R.id.btn_deposit);
        Button btnWithdraw = findViewById(R.id.btn_withdraw);
        Button btnGetBalance = findViewById(R.id.btn_get_balance);
        tvBalance = findViewById(R.id.tv_balance);

        // Initialize Web3j and load the contract
        web3 = Web3j.build(new HttpService("")); // Replace with your Infura project ID
        credentials = Credentials.create(PRIVATE_KEY);
        ContractGasProvider contractGasProvider = new DefaultGasProvider();

        // Load your contract
        contract = A.load(CONTRACT_ADDRESS, web3, credentials, contractGasProvider);

        // Set up button listeners
        btnDeposit.setOnClickListener(v -> deposit(new BigInteger("100"))); // Example deposit of 100 units
        btnWithdraw.setOnClickListener(v -> withdraw(new BigInteger("50"))); // Example withdraw of 50 units
        btnGetBalance.setOnClickListener(v -> getBalance());
    }

    // Function to deposit into the smart contract
    private void deposit(BigInteger amount)
    {
        contract.deposit(amount).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                try {
                    System.out.println(transactionReceipt);
                }catch (Exception e){
                    e.printStackTrace();
                }
               // Log.i("BlockchainApp", "Deposit Successful: " + transactionReceipt.getTransactionHash());
            }
        });
    }

    // Function to withdraw from the smart contract
    private void withdraw(BigInteger amount) {
        contract.withdraw(amount).flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<TransactionReceipt>() {
            @Override
            public void accept(TransactionReceipt transactionReceipt) throws Exception {
                try {
                    System.out.println(transactionReceipt);
                }catch (Exception e){
                    e.printStackTrace();
                }
              //  Log.i("BlockchainApp", "Withdraw Successful: " + transactionReceipt.getTransactionHash());
            }
        });
    }

    // Function to get the balance from the smart contract
    private void getBalance() {
        contract.getBalance().flowable().subscribeOn(Schedulers.io()).subscribe(new Consumer<BigInteger>() {
            @Override
            public void accept(BigInteger balance) throws Exception {
                try {
                    System.out.println(balance);
                }catch (Exception e){
                    e.printStackTrace();
                }
             //   Log.i("BlockchainApp", "Current Balance: " + balance.toString());
                // Update the balance text in the UI
               // runOnUiThread(() -> tvBalance.setText("Balance: " + balance.toString()));
            }
        });
    }
}

