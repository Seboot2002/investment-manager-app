package com.seboot.portpocket.service;

import com.seboot.portpocket.db.ActiveDao;
import com.seboot.portpocket.model.Active;
import com.seboot.portpocket.model.ActiveType;
import com.seboot.portpocket.model.Portfolio;

import java.time.LocalDate;

public class PortfolioService {

    private static PortfolioService instance;
    private final Portfolio portfolio = new Portfolio();
    private final ActiveDao dao = new ActiveDao();

    public PortfolioService() {
        portfolio.getAllActives().addAll(dao.getAll());
    }

    public static synchronized PortfolioService getInstance() {
        if (instance == null) {
            instance = new PortfolioService();
        }
        return instance;
    }

    public void addActive(
        String name,
        ActiveType type,
        double price,
        int quantity
    ) {
        Active active = new Active(
            name,
            type,
            price,
            price,
            quantity
        );

        dao.insert(active);
        portfolio.addActive(active);
    }

    public void deleteActive(Active active) {
        dao.delete(active);
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}
